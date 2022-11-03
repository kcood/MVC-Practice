package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v5.adaptor.ControllerV3HandlerAdaptor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();

    //어댑터가 여러개 담겨있고, 그 중에 하나를 찾아서 꺼내써야하니까
    private final List<MyHandlerAdaptor> handlerAdaptors = new ArrayList<>();

    public FrontControllerServletV5() {
        //매핑정보넣기
        initHandlerMappingMap();

        initHandlerAdaptors();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }
    private void initHandlerAdaptors() {
        handlerAdaptors.add(new ControllerV3HandlerAdaptor());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Object handler = getHandler(request);
        if (handler == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //v3냐, v4냐, 핸들러 어댑터 찾아오기
        MyHandlerAdaptor adaptor = getHandlerAdaptor(handler);

        ModelView mv = adaptor.handle(request, response, handler);


        String viewName = mv.getViewName(); //논리이름 (new-form)
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request,response);

    }


    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdaptor getHandlerAdaptor(Object handler) {
        for (MyHandlerAdaptor adaptor : handlerAdaptors) {
            if (adaptor.supports(handler)){
                return adaptor;
            }
        }
        throw new IllegalArgumentException("handelrAdaptor를 찾을 수 없습니다. handler= " + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
