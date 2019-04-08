package views.html.site

import lila.api.Context
import lila.app.templating.Environment._
import lila.app.ui.ScalatagsTemplate._

import controllers.routes

object help {

  def page(active: String, doc: io.prismic.Document, resolver: io.prismic.DocumentLinkResolver)(implicit ctx: Context) = {
    val title = ~doc.getText("doc.title")
    layout(
      title = title,
      active = active,
      moreCss = responsiveCssTag("page")
    )(main(cls := "page box box-pad")(
        h1(title),
        div(cls := "body")(raw(~doc.getHtml("doc.content", resolver)))
      ))
  }

  def webmasters()(implicit ctx: Context) = {
    val parameters = frag(
      p("Parameters:"),
      ul(
        li(strong("theme"), ": ", lila.pref.Theme.all.map(_.name).mkString(", ")),
        li(strong("bg"), ": light, dark")
      )
    )
    layout(
      title = "Webmasters",
      active = "webmasters"
    )(frag(
      div(cls := "box box-pad developers")(
        h1(id := "embed-tv")("Embed Lichess TV in your site"),
        raw("""<script src="/tv/embed?theme=wood&bg=light"></script>"""),
        p("Just add the following HTML to your site:"),
        pre("""<script src="https://lichess.org/tv/embed?theme=auto&bg=auto"></script>"""),
        parameters
      ),
      br,
      div(cls := "box box-pad developers")(
        h1(id := "embed-puzzle")("Embed the daily puzzle in your site"),
        raw("""<script src="/training/embed?theme=auto&bg=auto"></script>"""),
        p("Just add the following HTML to your site:"),
        pre("""<script src="https://lichess.org/training/embed?theme=auto&bg=auto"></script>"""),
        parameters,
        p("The text is automatically translated to your visitor's language.")
      ),
      br,
      div(cls := "box box-pad developers")(
        h1("Embed a chess analysis in your site"),
        raw("""<iframe width=530 height=353 src="https://lichess.org/study/embed/XtFCFYlM/GCUTf2Jk?bg=auto&theme=auto" frameborder=0 style="margin-bottom: 1em"></iframe>"""),
        p("Create ", a(href := routes.Study.allDefault(1), cls := "blue")("a study"), ", then click the share button to get the HTML code for the current chapter."),
        pre("""<iframe width=600 height=397 frameborder=0
src="https://lichess.org/study/embed/XtFCFYlM/GCUTf2Jk?theme=auto&bg=auto"
></iframe>"""),
        parameters,
        p("The text is automatically translated to your visitor's language.")
      ),
      br,
      div(cls := "box box-pad developers")(
        h1("Embed a chess game in your site"),
        raw("""<iframe width=530 height=353 src="https://lichess.org/embed/MPJcy1JW?bg=auto&theme=auto" frameborder=0 style="margin-bottom: 1em"></iframe>"""),
        p(raw("""On a game analysis page, click the <em>"FEN &amp; PGN"</em> tab at the bottom, then """), "\"", em(trans.embedInYourWebsite.frag(), "\".")),
        pre("""<iframe width="600" height="397" frameborder="0"
src="https://lichess.org/embed/MPJcy1JW?theme=auto&bg=auto"
></iframe>"""),
        parameters,
        p("The text is automatically translated to your visitor's language.")
      ),
      br,
      div(cls := "box box-pad developers")(
        h1("HTTP API"),
        p(raw("""Lichess exposes a RESTish HTTP/JSON API that you are welcome to use. Read the <a href="/api" class="blue">HTTP API documentation</a>."""))
      ),
      br,
      div(cls := "box box-pad developers")(
        h1(id := "widgets")("Lichess Widgets"),
        p("Let your website/blog visitors know that you're playing on lichess!"),
        p(raw("""See <a href="https://rubenwardy.com/lichess_widgets/" class="blue">https://rubenwardy.com/lichess_widgets/</a> for widgets with your username and rating."""))
      )
    ))
  }

  def layout(
    title: String,
    active: String,
    moreCss: Frag = emptyFrag,
    moreJs: Frag = emptyFrag
  )(body: Frag)(implicit ctx: Context) = views.html.base.layout(
    title = title,
    moreCss = moreCss,
    moreJs = moreJs,
    responsive = true
  ) {
    val sep = div(cls := "sep")
    val external = frag(" ", i(dataIcon := "0"))
    def activeCls(c: String) = cls := active.activeO(c)
    main(cls := "page-menu")(
      st.nav(cls := "page-menu__menu subnav")(
        a(activeCls("about"), href := routes.Page.about)(trans.aboutX.frag("lichess.org")),
        a(activeCls("faq"), href := routes.Main.faq)("FAQ"),
        a(activeCls("contact"), href := routes.Main.contact)(trans.contact.frag()),
        a(activeCls("tos"), href := routes.Page.tos)(trans.termsOfService.frag()),
        a(activeCls("privacy"), href := routes.Page.privacy)(trans.privacy.frag()),
        a(activeCls("master"), href := routes.Page.master)("Title verification"),
        sep,
        a(activeCls("help"), href := routes.Page.help)(trans.contribute.frag()),
        a(activeCls("thanks"), href := routes.Page.thanks)(trans.thankYou.frag()),
        sep,
        a(activeCls("webmasters"), href := routes.Main.webmasters)(trans.webmasters.frag()),
        a(activeCls("database"), href := "https://database.lichess.org")(trans.database.frag(), external),
        a(activeCls("api"), href := "https://database.lichess.org")("API", external),
        a(activeCls("source"), href := "https://github.com/ornicar/lila")("Source code", external),
        sep,
        a(activeCls("lag"), href := routes.Main.lag)("Is Lichess lagging?")
      ),
      div(cls := "page-menu__content")(body)
    )
  }
}