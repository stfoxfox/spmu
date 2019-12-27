package com.company.spmu.process.helper;

import java.util.UUID;

public interface IUpdateStatusHelper {
    void updateStatus(UUID entityId, String status);
}