package welper.welper.service

import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import welper.welper.controller.response.AccessTokenResponse
import welper.welper.external.WelfareResponse
import welper.welper.external.WelfareServiceI
import java.net.URI


@Service
class CategoryService(
    private val welfareService: WelfareServiceI,
) {
}