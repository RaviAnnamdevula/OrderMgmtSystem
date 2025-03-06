package com.jocata.oms.service.Impl;

import com.jocata.oms.data.um.dao.*;
import com.jocata.oms.datamodel.um.entity.*;
import com.jocata.oms.service.DataLoaderService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.sql.JDBCType.BOOLEAN;
import static java.sql.JDBCType.NUMERIC;
import static javax.management.openmbean.SimpleType.STRING;

@Service
public class DataLoaderServiceImpl implements DataLoaderService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressDao addressDao;


    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RefreshTokenDao refreshTokenDao;

    private String getCellValue(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue()); // for phone numbers or ZIP codes
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> null;
        };
    }

    @Override
    public void loadData(String filePath) {


        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header


                // Create UserEntity
                UserEntity user = UserEntity.builder()
                        .fullName(row.getCell(0).getStringCellValue())
                        .email(row.getCell(1).getStringCellValue())
                        .passwordHash(row.getCell(2).getStringCellValue())
                        .phone(getCellValue(row.getCell(3)))
                        .profilePicture(row.getCell(4).getStringCellValue())
                        .otpSecret(row.getCell(5).getStringCellValue())
                        .smsEnabled(row.getCell(6) != null && row.getCell(6).getCellType() == CellType.BOOLEAN
                                ? row.getCell(6).getBooleanCellValue()
                                : Boolean.parseBoolean(row.getCell(6).getStringCellValue()))
                        .isActive(row.getCell(7) != null && row.getCell(7).getCellType() == CellType.BOOLEAN
                        ? row.getCell(7).getBooleanCellValue()
                        : Boolean.parseBoolean(row.getCell(7).getStringCellValue()))
                        .build();

                userDao.saveUser(user);

                // Create AddressEntity
                AddressEntity address = new AddressEntity();
                address.setUser(user);
                address.setAddress(row.getCell(8).getStringCellValue());
                address.setCity(row.getCell(9).getStringCellValue());
                address.setState(row.getCell(10).getStringCellValue());
                address.setCountry(row.getCell(11).getStringCellValue());
                address.setZipCode(row.getCell(12).getStringCellValue());
                addressDao.saveAddress(address);

                // Create RoleEntity
                RoleEntity role = new RoleEntity();
                role.setRoleName(row.getCell(13).getStringCellValue());
                Set<RoleEntity> roles = new HashSet<>();
                roles.add(role);
                user.setRoles(roles);

                // Create PermissionEntity
                PermissionEntity permission = new PermissionEntity();
                permission.setPermissionName(row.getCell(14).getStringCellValue());
                permissionDao.savePermission(permission);
                role.setPermissions(new HashSet<>(Set.of(permission)));
               // role.getPermissions().add(permission);

                // Create RefreshTokenEntity
                RefreshTokenEntity refreshToken = new RefreshTokenEntity();
                refreshToken.setUser(user);
                refreshToken.setToken(row.getCell(15).getStringCellValue());
                refreshToken.setExpiresAt(LocalDateTime.parse(row.getCell(16).getStringCellValue()));
                refreshTokenDao.saveRefreshToken(refreshToken);

                userDao.saveUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> lines = reader.lines().skip(1).collect(Collectors.toList()); // Skip header

            for (String line : lines) {
                String[] data = line.split(",");

                // Create UserEntity
                UserEntity user = UserEntity.builder()
                        .fullName(data[0])
                        .email(data[1])
                        .passwordHash(data[2])
                        .phone(data[3])
                        .profilePicture(data[4])
                        .otpSecret(data[5])
                        .smsEnabled(Boolean.parseBoolean(data[6]))
                        .isActive(Boolean.parseBoolean(data[7]))
                        .build();
                // Save user first
                userDao.saveUser(user);

// Create and save address
                AddressEntity address = new AddressEntity();
                address.setUser(user);
                address.setAddress(data[8]);
                address.setCity(data[9]);
                address.setState(data[10]);
                address.setCountry(data[11]);
                address.setZipCode(data[12]);
                addressDao.saveAddress(address);

// Create and set roles
                RoleEntity role = new RoleEntity();
                role.setRoleName(data[13]);
                Set<RoleEntity> roles = new HashSet<>();
                roles.add(role);
                user.setRoles(roles);

// Create and save permissions
                PermissionEntity permission = new PermissionEntity();
                permission.setPermissionName(data[14]);
                permissionDao.savePermission(permission);
                role.setPermissions(new HashSet<>(Set.of(permission)));

// Create and save refresh token
                RefreshTokenEntity refreshToken = new RefreshTokenEntity();
                refreshToken.setUser(user);
                refreshToken.setToken(data[15]);
                refreshToken.setExpiresAt(LocalDateTime.parse(data[16]));
                refreshTokenDao.saveRefreshToken(refreshToken);

// Finally, update user with all relationships
                userDao.saveUser(user);

                *//*userDao.saveUser(user);

                // Create AddressEntity
                AddressEntity address = new AddressEntity();
                address.setUser(user);
                address.setAddress(data[8]);
                address.setCity(data[9]);
                address.setState(data[10]);
                address.setCountry(data[11]);
                address.setZipCode(data[12]);
                addressDao.saveAddress(address);

                // Create RoleEntity
                RoleEntity role = new RoleEntity();
                role.setRoleName(data[13]);
                Set<RoleEntity> roles = Set.of(role); // Java 9+ way to create an immutable set
                user.setRoles(roles);


                // Create PermissionEntity
                PermissionEntity permission = new PermissionEntity();
                permission.setPermissionName(data[14]);
                permissionDao.savePermission(permission);
                role.getPermissions().add(permission);

                // Create RefreshTokenEntity
                RefreshTokenEntity refreshToken = new RefreshTokenEntity();
                refreshToken.setUser(user);
                refreshToken.setToken(data[15]);
                refreshToken.setExpiresAt(LocalDateTime.parse(data[16]));
                refreshTokenDao.saveRefreshToken(refreshToken);*//*

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
