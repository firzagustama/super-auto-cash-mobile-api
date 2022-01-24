package id.superautocash.mobile.api.integration

import id.superautocash.mobile.api.controller.response.GetAllMenuResponse
import id.superautocash.mobile.api.entity.Menu
import id.superautocash.mobile.api.entity.User
import id.superautocash.mobile.api.enums.RoleEnum
import id.superautocash.mobile.api.repository.MenuRepository
import id.superautocash.mobile.api.repository.UserRepository
import io.jsonwebtoken.lang.Assert
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class MenuApiTests @Autowired constructor(
    val repository: MenuRepository,
    val userRepository: UserRepository
): BaseApiTests() {

    lateinit var merchantUser: User
    lateinit var menus: List<Menu>

    @BeforeAll
    fun createTestMenu() {
        merchantUser = userRepository.save(User(
            password = "1234!@#$",
            roleId = RoleEnum.MERCHANT.id,
            username = "merchant_menu_test",
            email = "merchant.menu@gmail.com",
            phoneNumber = "081938758372",
            fullName = "Merchant Menu",
        ))
        menus = listOf(
            Menu(
                merchantId = merchantUser.id!!,
                imageUrl = "https://awsimages.detik.net.id/community/media/visual/2020/12/08/food-cour-jakarta-utara-1.jpeg?w=539",
                name = "Ayam Goreng",
                price = 15000,
            ),
            Menu(
                merchantId = merchantUser.id!!,
                imageUrl = "https://awsimages.detik.net.id/community/media/visual/2020/12/08/food-cour-jakarta-utara-1.jpeg?w=539",
                name = "Ayam Suir",
                price = 16000
            ),
            Menu(
                merchantId = merchantUser.id!!,
                imageUrl = "https://awsimages.detik.net.id/community/media/visual/2020/12/08/food-cour-jakarta-utara-1.jpeg?w=539",
                name = "Ayam ayam",
                price = 20000
            ),
            Menu(
                merchantId = merchantUser.id!!,
                imageUrl = "https://awsimages.detik.net.id/community/media/visual/2020/12/08/food-cour-jakarta-utara-1.jpeg?w=539",
                name = "Ayam ayaman",
                price = 25000
            ),
            Menu(
                merchantId = merchantUser.id!!,
                imageUrl = "https://awsimages.detik.net.id/community/media/visual/2020/12/08/food-cour-jakarta-utara-1.jpeg?w=539",
                name = "Ayam yummy",
                price = 20000
            )
        )
        repository.saveAll(menus)
    }

    @AfterAll
    fun deleteTestItem() {
        userRepository.delete(merchantUser)
        repository.deleteAll(menus)
    }

    @Test
    fun getAllMenu() {
        val response = get("/menu/${merchantUser.id}", GetAllMenuResponse::class)

        Assert.isTrue(response.success)
        Assert.notNull(response.data?.info)
        Assert.notEmpty(response.data?.menus)
    }

    @Test
    fun getAllMenu_pagination() {
        var response = get("/menu/${merchantUser.id}?page=0&size=3", GetAllMenuResponse::class)

        Assert.isTrue(response.success)
        Assert.notNull(response.data?.info)
        Assert.notNull(response.data?.info?.next)
        Assert.notEmpty(response.data?.menus)

        // check next url
        response = get(response.data!!.info.next!!, GetAllMenuResponse::class)

        Assert.isTrue(response.success)
        Assert.notNull(response.data?.info)
        Assert.notNull(response.data?.info?.prev)
        Assert.notEmpty(response.data?.menus)

        // check prev url
        response = get(response.data!!.info.prev!!, GetAllMenuResponse::class)

        Assert.isTrue(response.success)
        Assert.notNull(response.data?.info)
        Assert.isNull(response.data?.info?.prev)
        Assert.notNull(response.data?.info?.next)
        Assert.notEmpty(response.data?.menus)
    }
}