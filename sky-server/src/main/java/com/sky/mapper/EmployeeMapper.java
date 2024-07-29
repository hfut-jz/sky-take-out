package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入员工数据
     * @param employee
     */
    @AutoFill(value= OperationType.INSERT)
    @Insert("insert into employee(name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user,status) " +
            "values" +
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})")
    void insert(Employee employee);

    /**
     *分页查询
     * @return
     */
    // 已完成完成其的xml文件配置
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用
     * @param employee
     * @return
     */
    @AutoFill(OperationType.UPDATE)
    Integer update(Employee employee);

    /**
     * 编辑员工类
     * Update from
     * @param employeeDTO
     */
    void edits(EmployeeDTO employeeDTO);
}
