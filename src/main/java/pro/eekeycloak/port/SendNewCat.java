package pro.eekeycloak.port;


import org.apache.cxf.interceptor.InInterceptors;
import pro.eekeycloak.annotation.AllowRole;
import ru.skpari.core.domain.commission.CatType;

import javax.ejb.Stateless;
import javax.jws.WebService;


@Stateless
@WebService(
        name = "SendNewCat",
        serviceName = "SendCatsService",
        portName = "SendCatsPort",
        endpointInterface = "ru.skpari.core.services.commission.SendCatsPort",
        targetNamespace = "http://skpari.ru/core/services/commission/",
        wsdlLocation = "META-INF/wsdl/SendCats.wsdl"
)
@InInterceptors(interceptors = {"pro.eekeycloak.interceptor.LoginInterceptor"})
@AllowRole(roles = {"user", " admin"})
public class SendNewCat {

    public CatType getCat() {
        return new CatType() {{
            setName("Kolia");
        }};
    }
}
