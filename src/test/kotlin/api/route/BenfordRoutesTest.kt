package com.johan.api.route

import com.johan.api.model.BenfordCheckResponse
import com.johan.module
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class BenfordRoutesTest {

    @Test
    fun `valid request returns OK`() = testApplication {
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
                    "text": "Invoice 123.45 payment 45 refund 302 amount 987",
                    "significanceLevel": 0.05
                }
            """.trimIndent()
            )
        }

        assertEquals(OK, response.status)

        val result: BenfordCheckResponse = response.body()
        assertEquals(true, result.followsBenford)
        assertEquals(
            mapOf(1 to 1L, 2 to 0L, 3 to 1L, 4 to 1L, 5 to 0L, 6 to 0L, 7 to 0L, 8 to 0L, 9 to 1L),
            result.observedDistribution
        )
        assertEquals(
            mapOf(
                1 to 1.204,
                2 to 0.704,
                3 to 0.5,
                4 to 0.388,
                5 to 0.316,
                6 to 0.268,
                7 to 0.232,
                8 to 0.204,
                9 to 0.184
            ), result.expectedDistribution
        )
    }

    @Test
    fun `missing significanceLevel returns BadRequest`() = testApplication {
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
                    "text": "Invoice 123.45 payment 45 refund 302 amount 987"
                }
            """.trimIndent()
            )
        }

        assertEquals(HttpStatusCode.Companion.BadRequest, response.status)
    }

    @Test
    fun `missing text returns BadRequest`() = testApplication {
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

        assertEquals(HttpStatusCode.Companion.BadRequest, response.status)
    }

    @Test
    fun `invalid values returns UnprocessableEntity`() = testApplication {
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
                    "text": "Invoice 123.45 payment 45 refund 302 amount 987",
                    "significanceLevel": -0.05
                }
            """.trimIndent()
            )
        }

        assertEquals(HttpStatusCode.Companion.UnprocessableEntity, response.status)
    }
}