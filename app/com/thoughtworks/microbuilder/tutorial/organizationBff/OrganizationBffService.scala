package com.thoughtworks.microbuilder.tutorial.organizationBff

import com.thoughtworks.microbuilder.tutorial.githubSdk.model.OrganizationSummary
import com.thoughtworks.microbuilder.tutorial.githubSdk.rpc.IOrganizationService
import com.thoughtworks.microbuilder.tutorial.organizationBffSdk.model.{BffOrganization, BffOrganizationList}
import com.thoughtworks.microbuilder.tutorial.organizationBffSdk.rpc.IOrganizationBffService
import scalaz.std.scalaFuture._

import scala.concurrent.{ExecutionContext, Future}
import com.thoughtworks.each.Monadic._
import com.thoughtworks.microbuilder.play.Implicits._

/**
  * @author 杨博 (Yang Bo) &lt;pop.atry@gmail.com&gt;
  */
class OrganizationBffService(organizationService: IOrganizationService)(implicit ec: ExecutionContext) extends IOrganizationBffService {
  override def listUserOrganizations(username: String) = throwableMonadic[Future] {
    val response = new BffOrganizationList
    response.name = username
    val githubOrganizationsFuture: Future[Array[OrganizationSummary]] = organizationService.listUserOrganizations(username)
    val githubOrganizations = githubOrganizationsFuture.each
    response.organizations = for {
      githubOrganization <- githubOrganizations
    } yield {
      val bffOrganization = new BffOrganization
      bffOrganization.description = githubOrganization.description
      bffOrganization.iconUrl = githubOrganization.avatar_url
      bffOrganization.name = githubOrganization.login
      bffOrganization
    }
    response
  }
}
