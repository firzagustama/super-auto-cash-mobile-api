package id.superautocash.mobile.api.integration

import id.superautocash.mobile.api.controller.request.CreateMenuRequest
import id.superautocash.mobile.api.controller.request.UpdateMenuRequest
import id.superautocash.mobile.api.controller.response.BaseResponse
import id.superautocash.mobile.api.controller.response.CreateMenuResponse
import id.superautocash.mobile.api.controller.response.GetAllMenuResponse
import id.superautocash.mobile.api.controller.response.GetMenuResponse
import id.superautocash.mobile.api.entity.Menu
import id.superautocash.mobile.api.entity.User
import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import id.superautocash.mobile.api.enums.RoleEnum
import id.superautocash.mobile.api.repository.MenuRepository
import id.superautocash.mobile.api.repository.UserRepository
import io.jsonwebtoken.lang.Assert
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class MenuApiTests @Autowired constructor(
    val repository: MenuRepository,
    val userRepository: UserRepository,
): BaseApiTests() {

    lateinit var testUser: User
    lateinit var merchantUser: User
    lateinit var illegalMerchantUser: User
    lateinit var menus: List<Menu>

    @BeforeEach
    fun initHeader() {
        clearHeader()
    }

    @BeforeAll
    fun createTestMenu() {
        testUser = userRepository.save(User(
            password = "1234!@#$",
            roleId = RoleEnum.USER.id,
            username = "test_user",
            email = "test.user@gmail.com",
            phoneNumber = "081938478273",
            fullName = "Test User Menu"
        ))
        merchantUser = userRepository.save(User(
            password = "1234!@#$",
            roleId = RoleEnum.MERCHANT.id,
            username = "merchant_menu_test",
            email = "merchant.menu@gmail.com",
            phoneNumber = "081938758372",
            fullName = "Merchant Menu",
        ))
        illegalMerchantUser = userRepository.save(User(
            password = "1234!@#$",
            roleId = RoleEnum.MERCHANT.id,
            username = "illegal_merchant_menu_test",
            email = "illegal_merchant.menu@gmail.com",
            phoneNumber = "081938758372",
            fullName = "Illegal Merchant Menu",
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
            ),
            Menu(
                merchantId = merchantUser.id!!,
                imageUrl = "https://awsimages.detik.net.id/community/media/visual/2020/12/08/food-cour-jakarta-utara-1.jpeg?w=539",
                name = "Ayam bakal dihapus",
                price = 20000
            )
        )
        repository.saveAll(menus)
    }

    @AfterAll
    fun deleteTestItem() {
        userRepository.deleteAll(arrayListOf(testUser, merchantUser, illegalMerchantUser))
        repository.deleteAll(menus)
    }

    @Test
    fun getMenu_notFound() {
        val response = get("/menu/detail/0", GetMenuResponse::class)

        Assert.isTrue(!response.success)
        Assert.isTrue(response.errorCode == GeneralExceptionEnum.MENU_NOT_FOUND.errorCode)
    }

    @Test
    fun getMenu_found() {
        val response = get("/menu/detail/${menus[0].id}", GetMenuResponse::class)

        Assert.isTrue(response.success)
        Assert.notNull(response.data)
        Assert.notNull(response.data!!.menu.id)
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

    @Test
    fun create_forbiddenRole() {
        useValidToken(testUser)
        val request = CreateMenuRequest(
            name = "Ayam PokPok",
            imageUrl = "https://testiimage",
            price = 10000,
            description = "Lezat loh"
        )
        val response = post("/menu/create", request, CreateMenuResponse::class)

        Assert.isTrue(!response.success)
        Assert.isTrue(response.errorCode == GeneralExceptionEnum.FORBIDDEN.errorCode)
    }

    @Test
    fun create_success() {
        useValidToken(merchantUser)
        val request = CreateMenuRequest(
            name = "Ayam PokPok",
            imageUrl = "https://testiimage",
            price = 10000,
            description = "Lezat loh"
        )
        val response = post("/menu/create", request, CreateMenuResponse::class)

        Assert.isTrue(response.success)
        Assert.notNull(response.data)
        Assert.notNull(response.data!!.id)

        repository.deleteById(response.data!!.id)
    }

    @Test
    fun update_notFound() {
        useValidToken(merchantUser)
        val request = UpdateMenuRequest(
            id = 0,
            name = menus[0].name,
            imageUrl = menus[0].imageUrl,
            price = menus[0].price,
            description = menus[0].description
        )
        val response = post("/menu/update", request, GetMenuResponse::class)

        Assert.isTrue(!response.success)
        Assert.isTrue(response.errorCode == GeneralExceptionEnum.MENU_NOT_FOUND.errorCode)
    }

    @Test
    fun update_merchantIllegal() {
        useValidToken(illegalMerchantUser)
        val request = UpdateMenuRequest(
            id = menus[0].id,
            name = menus[0].name,
            imageUrl = menus[0].imageUrl,
            price = menus[0].price,
            description = menus[0].description
        )
        val response = post("/menu/update", request, GetMenuResponse::class)

        Assert.isTrue(!response.success)
        Assert.isTrue(response.errorCode == GeneralExceptionEnum.FORBIDDEN.errorCode)
    }

    @Test
    fun update_success() {
        useValidToken(merchantUser)
        val request = UpdateMenuRequest(
            id = menus[0].id,
            name = "Ayam goreng update",
            imageUrl = menus[0].imageUrl,
            price = menus[0].price,
            description = menus[0].description
        )
        val response = post("/menu/update", request, GetMenuResponse::class)

        Assert.isTrue(response.success)
        Assert.notNull(response.data)
        Assert.notNull(response.data!!.menu)
        Assert.isTrue(response.data!!.menu.name == request.name)
        Assert.isTrue(response.data!!.menu.updatedDate != response.data!!.menu.createdDate)
    }

    @Test
    fun delete_notFound() {
        useValidToken(merchantUser)
        val response = get("/menu/delete/0", BaseResponse::class)

        Assert.isTrue(!response.success)
        Assert.isTrue(response.errorCode == GeneralExceptionEnum.MENU_NOT_FOUND.errorCode)
    }

    @Test
    fun delete_merchantIllegal() {
        useValidToken(illegalMerchantUser)
        val response = get("/menu/delete/${menus[0].id}", BaseResponse::class)

        Assert.isTrue(!response.success)
        Assert.isTrue(response.errorCode == GeneralExceptionEnum.FORBIDDEN.errorCode)
        Assert.notNull(repository.findById(menus[0].id!!))
    }

    @Test
    fun delete_success() {
        useValidToken(merchantUser)
        val response = get("/menu/delete/${menus[5].id}", BaseResponse::class)

        Assert.isTrue(response.success)
        Assert.isTrue(!repository.findById(menus[5].id!!).isPresent)
    }
}