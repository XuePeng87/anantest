package cn.woyun.anov.biz;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 用于验证某个值是否存在的工具类。
 * 通常在创建或者修改业务时，判断某个属性的值是否已经在系统中。
 *
 * @author xuepeng
 */
public class ExistsUtil {

    /**
     * 构造函数。
     */
    private ExistsUtil() {
    }

    /**
     * 判断值是否存在。
     * 存在返回true，不存在返回false。
     *
     * @param list  根据要验证的值查询出的数据列表。
     * @param value 要验证是否存在的值。
     * @return 是否存在。
     */
    public static <T> boolean exists(final List<T> list, final String value, final String field) {
        return exists(list, value, (l, v) -> {
            if (l.size() > 1) {
                // 如果list中的数据数量大于1哥，则表示已经存在了，返回ture
                return true;
            } else if (l.isEmpty()) {
                // 如果list中没有记录，则认为是要新增，此时返回false
                return false;
            } else {
                // 如果list中只有一行记录，且value相等，则认为是对某个数据进行修改，此时返回true
                // 场景为修改时，如果list中的数据就是要修改的数据，则返回false，如果list中的数据不是要修改的数据，则返回true
                final MethodAccess access = MethodAccess.get(l.get(0).getClass());
                final String id = access.invoke(l.get(0), "get" + field).toString();
                return StringUtils.isEmpty((CharSequence) v) || !StringUtils.equals(id, (CharSequence) v);
            }
        });
    }

    /**
     * 判断值是否存在。
     *
     * @param list           数据列表。
     * @param value          要验证是否存在的值。
     * @param existsFunction 验证存在的Lambda接口。
     * @return 是否存在。
     */
    public static <T> boolean exists(final List<T> list, final String value, final ExistsFunction<T> existsFunction) {
        return existsFunction.exists(list, value);
    }

}
