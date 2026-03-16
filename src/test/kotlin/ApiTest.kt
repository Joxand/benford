package com.johan

import com.johan.api.model.BenfordRequest
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApiTest {

    @Test
    fun validRequestResponseShouldReturnOk() = testApplication {
        application {
            module()
        }
        client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/api/v1/benford/check") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                { 
                    "input": "Invoice 123.45 payment 45 refund 302 amount 987",
                    "significanceLevel": 0.05
                }
            """.trimIndent()
            )
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun missingSignificanceLevelShouldReturnBadRequest() = testApplication {
        application {
            module()
        }
        client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/api/v1/benford/check") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                { 
                    "input": "Invoice 123.45 payment 45 refund 302 amount 987"
                }
            """.trimIndent()
            )
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun missingInputShouldReturnBadRequest() = testApplication {
        application {
            module()
        }
        client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/api/v1/benford/check") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                { 
                    "significanceLevel": 0.05
                }
            """.trimIndent()
            )
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}
