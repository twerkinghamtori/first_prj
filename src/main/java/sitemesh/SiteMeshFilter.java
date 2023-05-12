package sitemesh;
/*
 * sitemesh : 화면에 layout을 코딩한 jsp를 페이지에 적용
 * 
 * .addDecoratorPath("/model1/member/*", "/layout/layout.jsp")
 * 		- url : /model1/member/ 로 시작하는 모든 요청 url
 * 		- layout : 응답시 layout 페이지용으로 사용되는 jsp 페이지 설정
 * .addExcludedPath("/model1/member/id*")
 * 		- url : /model1/member/id* 로 시작하는 모든 요청시 layout제외
 * */

import javax.servlet.annotation.WebFilter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

@WebFilter("/*")
public class SiteMeshFilter extends ConfigurableSiteMeshFilter {
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder.addDecoratorPath("/*", "/layout/mainlayout.jsp")
		 .addExcludedPath("/member/nick*")
		 .addExcludedPath("/member/email*")
		 .addExcludedPath("/member/password*")
		 .addExcludedPath("/member/picture*")
		.addExcludedPath("/member/pw*")
		.addExcludedPath("/event/picture*")
		.addExcludedPath("/board/comm*")
		.addExcludedPath("/ajax/*");
	}
}

