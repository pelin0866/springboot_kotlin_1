//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//
//class testDeneme {
//
//    private val dataSource = MockBankDataSource()
//
//    @Test
//    fun `should provide the size of banks`() {
//        val banks = dataSource.retriveBanks()
//        assertThat(banks.size).isGreaterThanOrEqualTo(3)
//    }
//
//    @Test
//    fun `should provide some mock data`() {
//        val banks = dataSource.retriveBanks()
//
//        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
//        assertThat(banks).anyMatch { it.trust != 0.0 } //banks içindeki değerlerden en az herhangi birinin trust değerinin sıfıra eşit olmaması
//    }
//
//    //spesifik bir accountNumber retrive edebiliyor mu
//
//    @Test
//    fun `should provide specific accountNumber`() {
//        val bank = dataSource.retriveBank("123")
//
//        assertThat(bank).isNotNull() //nesnenin gerçekten var olup olmadığını kontrol eder, eğer yoksa NullPointException almayalım diye
//        assertThat(bank.accountNumber).isEqualTo("123") //eğer varsa nesnenin içeriğini doğrular
//    }
//
//    //olmayan bir accountNumber ağırıldığında hata veriyor mu
//    @Test
//    fun `should provide exception when accountNumber DNE`(){
//        assertThrows<NoSuchElementException> {
//            dataSource.retriveBank("999")
//        }
//
//    //yeni bir bank account oluşturma testi
//    @Test
//    fun `should be added new account in the list`(){
//       //given
//        val newbank = Bank("999", 5.0, 42)
//        //when
//        dataSource.createBank(newbank)
//        //then
//        val retrived = dataSource.retrivedBank("999")
//        assertThat(retrived).isNotNull() //var mı kontrolü //assertThat(newBank) kullanamazsın, çünkü bu nesne zaten var ve biz onu kendimiz oluşturduk.
//        assertThat(retrived).isEqualTo(newbank) //Testin amacı: veri kaynağından geri aldığımız nesne doğru mu? Bu yüzden retrieved test edilmelidir.
//
//    }
//
//    @Test
//    fun `should give exception when duplicate happen`(){
//        val dublicateBank = Bank("123", 4.0, 42) //zaten var olan Bank adı
//
//        assertThrows<IllegalArgumentException> { //bununla sadece exception tipi kontrol edilir.
//            dataSource.createBank(dublicateBank)
//        }
//        //İstersen fırlatılan exception mesajını da kontrol edebiliriz:
//        //val ex = assertThrows<IllegalArgumentException> {
//        //    dataSource.createBank(duplicateBank)
//        //}
//        //assertThat(ex.message).contains("already exists")
//    }
//
//    @Test
//    fun `should update an existing bank`() {
//        val updatedBank = Bank("123", 5.0, 41)
//
//        dataSource.updatedBank(updatedBank)
//
//        val retrived = dataSource.retrivedBank("123")
//        assertThat(retrived.trust).isEqualTo(updatedBank.trust)
//        assertThat(retrived.transactionFee).isEqualTo(updatedBank.transactionFee)
//    }
//
//    @Test
//    fun `not updateing non-existing bank`() {
//        val nonexisting = Bank("999", 4, 4)
//
//
//
//        assertThrows<NoSuchElementException> {
//            dataSource.updateBank(nonexisting)
//        }
//    }
//}