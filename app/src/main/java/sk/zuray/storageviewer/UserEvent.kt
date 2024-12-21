package sk.zuray.storageviewer

sealed interface UserEvent {
    object SearchForCustomer: UserEvent
    data class CustomerName(val name: String): UserEvent
    object SearchForProduct: UserEvent
    data class ProductName(val name: String): UserEvent
    object ShowAllOrders: UserEvent
    object LoadDatabaseFromCSV: UserEvent
    object MarkAsServed: UserEvent
    object ReturnToMenu: UserEvent
}
