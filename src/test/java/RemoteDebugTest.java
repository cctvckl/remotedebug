
import com.remotedebug.service.IRedisCacheService;
import com.remotedebug.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RemoteDebugTest {
    public void debug(){
        IRedisCacheService bean = SpringContextUtils.getBean(IRedisCacheService.class);
        String value = bean.getCount("123456789");

        log.info("value:{}", value );
    }

    public static void main(String[] args) {
        new RemoteDebugTest().debug();
    }
}
