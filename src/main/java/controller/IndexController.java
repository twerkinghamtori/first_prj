package controller;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import gdu.mskim.MskimRequestMapping;

@WebServlet(urlPatterns = {"/index"},
initParams = {@WebInitParam(name="view", value="/")}
		)
public class IndexController extends MskimRequestMapping{}
