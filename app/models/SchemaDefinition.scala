package models

import sangria.schema.{Field, _}

/**
 * Defines a GraphQL schema for the current project
 */
object SchemaDefinition {

  val Tshirt= ObjectType(
    "Tshirt",
    fields[Unit, Tshirt](
      Field("id", IntType, resolve = _.value.id),
      Field("color", StringType, resolve = _.value.color),
      Field("pic_url", StringType, resolve = _.value.pic_url),
      Field("price", IntType, resolve = _.value.price)
    )
  )

  val Query = ObjectType(
    "Query", fields[Tshirt, Unit](
      Field("tshirts", ListType(Tshirt),
        description = Some("Returns a list of all available books."),
        resolve = _.ctx.getAll())
    ))

  val TshirtSchema = Schema(Query)
}
