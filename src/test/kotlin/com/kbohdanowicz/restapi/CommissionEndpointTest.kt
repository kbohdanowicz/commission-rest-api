package com.kbohdanowicz.restapi

import com.kbohdanowicz.restapi.app.controller.CommissionController
import com.kbohdanowicz.restapi.app.model.CommissionCalculationResponse
import com.kbohdanowicz.restapi.app.service.calculation.CommissionCalculationServiceImpl
import com.kbohdanowicz.restapi.app.service.db.mongo.CommissionMongoService
import com.kbohdanowicz.restapi.app.service.parsing.CustomerIdParsingServiceImpl
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ContextConfiguration(classes = [
    CustomerIdParsingServiceImpl::class,
    CommissionCalculationServiceImpl::class,
])
@Import(CommissionController::class)
@WebMvcTest(CommissionController::class)
class CommissionEndpointTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var mongoService: CommissionMongoService

    @BeforeEach
    fun setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    fun `Should return 401 status code when accessing endpoint without credentials`() {
        mvc.perform(createGetCommissionRequest("1"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser
    fun `Should return one commission for one valid user`() {
        with(CommissionCalculationResponse) {
            mvc.perform(createGetCommissionRequest("1"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$['$CUSTOMER_ID']", `is`(1)))
                .andExpect(jsonPath("$['$FIRST_NAME']", `is`("Andrzej")))
                .andExpect(jsonPath("$['$LAST_NAME']", `is`("Andrzejowski")))
        }
    }

    @Test
    @WithMockUser
    fun `Should return two commissions for two valid users`() {
        with(CommissionCalculationResponse) {
            mvc.perform(createGetCommissionRequest("1, 2"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]['$CUSTOMER_ID']", `is`(1)))
                .andExpect(jsonPath("$[0]['$FIRST_NAME']", `is`("Andrzej")))
                .andExpect(jsonPath("$[0]['$LAST_NAME']", `is`("Andrzejowski")))
                .andExpect(jsonPath("$[1]['$CUSTOMER_ID']", `is`(2)))
                .andExpect(jsonPath("$[1]['$FIRST_NAME']", `is`("Anna")))
                .andExpect(jsonPath("$[1]['$LAST_NAME']", `is`("Zaradna")))
        }
    }

    @Test
    @WithMockUser
    fun `Should return one commission for one valid user and one invalid user`() {
        with(CommissionCalculationResponse) {
            mvc.perform(createGetCommissionRequest("1, 7"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$['$CUSTOMER_ID']", `is`(1)))
                .andExpect(jsonPath("$['$FIRST_NAME']", `is`("Andrzej")))
                .andExpect(jsonPath("$['$LAST_NAME']", `is`("Andrzejowski")))
        }
    }

    @Test
    @WithMockUser
    fun `Should return an error message for an invalid user`() {
        mvc.perform(createGetCommissionRequest("7"))
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(CommissionController.INVALID_CUSTOMER_ID_MESSAGE))
    }

    private fun createGetCommissionRequest(customerId: String) =
        get(CommissionController.COMMISSION_ENDPOINT)
            .param(CommissionController.COMMISSION_CUSTOMER_ID_PARAM, customerId)
            .contentType(MediaType.APPLICATION_JSON)
}
