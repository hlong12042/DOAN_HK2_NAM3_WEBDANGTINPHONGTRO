package ptithcm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ptithcm.entity.Account;

@Transactional
public class ChuTroAuthorizeInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	SessionFactory factory;
	
	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("account") == null) {
			response.sendRedirect(request.getContextPath() 
					+ "/login.htm");
			return false;
		} else {
			Session session2 = factory.openSession();
			Transaction t = session2.beginTransaction();
			try {
			Account account = (Account) session.getAttribute("account");
			Account account2 = (Account) session2.get(Account.class, account.getUsername());
			if (!account2.getUsername().equals(account.getUsername())||!account2.getPassword().equals(account.getPassword())) {
				response.sendRedirect(request.getContextPath() 
						+ "/login.htm");
				return false;
			}
			if (account.getRole().getId() == 2) {
				response.sendRedirect(request.getContextPath() 
						+ "/khachthue/index.htm");
				return false;
			} else if (account.getRole().getId() == 3) {
				response.sendRedirect(request.getContextPath() 
						+ "/admin/index.htm");
				return false;
			}
			}catch (Exception e) {
				response.sendRedirect(request.getContextPath() 
						+ "/login.htm");
				return false;
			}
			return true;
		}

	}
}
