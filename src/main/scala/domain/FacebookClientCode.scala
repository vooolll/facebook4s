package domain

/**
  * Case class that represents facebook client code, can be exchanged to access_token
  * @param code string representation of client_code
  * @param machineId optional value that helps to identify specified client
  */
final case class FacebookClientCode(code: String, machineId: Option[String])