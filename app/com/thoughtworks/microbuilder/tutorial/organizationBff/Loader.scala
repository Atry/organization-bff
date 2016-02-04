package com.thoughtworks.microbuilder.tutorial.organizationBff

import com.thoughtworks.microbuilder.play.{RpcEntry, PlayOutgoingJsonService, RpcController}
import com.thoughtworks.microbuilder.tutorial.githubSdk.proxy.MicrobuilderOutgoingProxyFactory._
import com.thoughtworks.microbuilder.tutorial.githubSdk.proxy.MicrobuilderRouteConfigurationFactory._
import com.thoughtworks.microbuilder.tutorial.organizationBffSdk.proxy.MicrobuilderIncomingProxyFactory._
import com.thoughtworks.microbuilder.tutorial.organizationBffSdk.proxy.MicrobuilderRouteConfigurationFactory._
import com.thoughtworks.microbuilder.tutorial.githubSdk.rpc.IOrganizationService
import com.thoughtworks.microbuilder.tutorial.organizationBffSdk.rpc.IOrganizationBffService
import play.api.libs.ws.ning.NingWSComponents
import play.api.{BuiltInComponentsFromContext, Application, ApplicationLoader}
import play.api.ApplicationLoader.Context
import router.Routes

/**
  * @author 杨博 (Yang Bo) &lt;pop.atry@gmail.com&gt;
  */
class Loader extends ApplicationLoader {
  override def load(context: Context): Application = {

    val components = new BuiltInComponentsFromContext(context) with NingWSComponents {
      implicit def executionContext = actorSystem.dispatcher

      lazy val organizationService = PlayOutgoingJsonService.newProxy[IOrganizationService]("https://api.github.com/", wsApi)

      lazy val bffService = new OrganizationBffService(organizationService)

      lazy val rpcController = new RpcController(Seq(RpcEntry.implementedBy[IOrganizationBffService](bffService)))

      override lazy val router = new Routes(httpErrorHandler, rpcController)
    }

    components.application
  }
}