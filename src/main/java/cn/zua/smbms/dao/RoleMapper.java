package cn.zua.smbms.dao;

import cn.zua.smbms.bean.Role;
import java.util.List;

public interface RoleMapper {
    /**
     * ����������ҳ��ѯ��Ӧ����Ϣ
     * @param userName
     * @param userRole
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Role> selectProviderByPageAndCondition(String proCode, String proName, int pageIndex, int pageSize);

    /**
     * ����������ѯ��Ӧ������
     * @param userName
     * @param userRole
     * @return
     */
    int getCountByCondition(String proCode, String proName);

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);
}