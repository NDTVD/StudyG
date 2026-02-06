package com.learningplatform.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.learningplatform.model.User;
import com.learningplatform.model.Subject;
import com.learningplatform.service.ISubjectService;
import com.learningplatform.service.impl.SubjectServiceImpl;
import com.learningplatform.util.ServletUtils;

@WebServlet("/subject")
public class SubjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ISubjectService subjectService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.subjectService = new SubjectServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = ServletUtils.getCurrentUser(request);
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            List<Subject> availableSubjects = subjectService.getAvailableSubjects(user.getUserId());
            List<Subject> completedSubjects = subjectService.getCompletedSubjects(user.getUserId());
            
            request.setAttribute("availableSubjects", availableSubjects);
            request.setAttribute("completedSubjects", completedSubjects);
            request.getRequestDispatcher("jsp/subject.jsp").forward(request, response);
        } catch (Exception e) {
            ServletUtils.setErrorMessage(request, "Error loading subjects: " + e.getMessage());
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = ServletUtils.getCurrentUser(request);
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        String subjectIdStr = request.getParameter("subjectId");

        try {
            if ("viewDetails".equals(action) && subjectIdStr != null) {
                int subjectId = Integer.parseInt(subjectIdStr);
                Subject subject = subjectService.getSubjectById(subjectId);
                
                if (subject != null) {
                    request.setAttribute("subject", subject);
                    request.getRequestDispatcher("jsp/subject-detail.jsp").forward(request, response);
                } else {
                    ServletUtils.setErrorMessage(request, "Subject not found");
                    response.sendRedirect("subject");
                }
            }
        } catch (Exception e) {
            ServletUtils.setErrorMessage(request, "Error: " + e.getMessage());
            response.sendRedirect("subject");
        }
    }
}
