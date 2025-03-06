package com.jocata.oms.data.um.dao;

import com.jocata.oms.datamodel.um.entity.RefreshTokenEntity;

public interface RefreshTokenDao {
    void saveRefreshToken(RefreshTokenEntity refreshToken);
}
