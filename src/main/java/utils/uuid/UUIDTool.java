package utils.uuid;

import java.util.UUID;

/**
 * @description: UUID工具类
 * @author: Fearon
 * @create: 2018-04-12 16:57
 **/
public class UUIDTool {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getUUIDWithPrefix(String prefix){
        return prefix + getUUID();
    }

    public static String [] getUUIDs(int qulity){
        String [] uuids = new String[qulity];
        for(int i=0; i<qulity; i++)
            uuids[i] = getUUID();

        return uuids;
    }

    /**
     * 判断是否为正常的UUID
     * @param uuid
     * @return
     */
    public static boolean isUUID(String uuid){
        char [] uuid_char = uuid.toCharArray();
        if(uuid_char.length > 64)
            return false;
        return true;
    }

    public static void main(String[] args) {
        for (int i=0; i<6; i++)
            System.out.println(getUUIDWithPrefix("fearon"));
    }
}
