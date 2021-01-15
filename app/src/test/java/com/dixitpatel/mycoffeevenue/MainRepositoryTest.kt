@file:Suppress("SpellCheckingInspection")

package com.dixitpatel.mycoffeevenue
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.dixitpatel.mycoffeevenue.constant.LAT_LONG_STATIC
import com.dixitpatel.mycoffeevenue.network.ApiInterface
import com.dixitpatel.mycoffeevenue.repository.MainViewRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class MainRepositoryTest {

  private lateinit var repository: MainViewRepository
  private lateinit var client: ApiInterface
  private lateinit var retrofit: Retrofit

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    client =   retrofit.create(ApiInterface::class.java)
    repository = MainViewRepository()
  }

  @ExperimentalTime
  @Test
  fun fetchListFromNetworkTest() = runBlocking {

    repository.getVenueApiCall(
      apiInterface = client,
      LAT_LONG_STATIC,
      onSuccess = {},
      onError = {}
    ).test {
      assertEquals(expectItem()[0].venue.name, MockUtil.venue().name)
      assertEquals(expectItem()[0].venue.id, MockUtil.venue().id)
      assertEquals(expectItem(), MockUtil.mockNearByPlaceList())
      expectComplete()
    }
  }

}
