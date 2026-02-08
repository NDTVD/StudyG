package com.studg.servlet;

import com.studg.service.SimpleSubjectService;
import com.studg.service.SubjectService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SubjectServlet extends HttpServlet {
    private SubjectService subjectService;

    @Override
    public void init() throws ServletException {
        subjectService = new SimpleSubjectService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("subjects", subjectService.availableSubjects());
        req.getRequestDispatcher("subjects.jsp").forward(req, resp);
    }
}
