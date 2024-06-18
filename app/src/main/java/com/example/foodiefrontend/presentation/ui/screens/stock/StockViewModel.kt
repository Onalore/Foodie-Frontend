import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefrontend.service.BackendApi
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class StockViewModel : ViewModel() {

    private val _productType = MutableLiveData<String>()
    val productType: LiveData<String> get() = _productType

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun findProductByEan(ean: String) {
        viewModelScope.launch {
            try {
                Log.d("StockViewModel", "findProductByEan called with EAN: $ean")
                val response = BackendApi.createStockService().findProductByEan(ean).awaitResponse()
                if (response.isSuccessful) {
                    val eanResponse = response.body()
                    _productType.postValue(eanResponse?.tipo)
                } else {
                    Log.d("StockViewModel", "Error fetching product: ${response.message()}")
                    _error.postValue("No se pudo encontrar el producto: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("StockViewModel", "Exception fetching product: $e")
                _error.postValue("Error de excepción: ${e.message}")
            }
        }
    }
}
