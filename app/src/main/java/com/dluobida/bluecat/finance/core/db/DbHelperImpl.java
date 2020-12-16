/*
 * project ：JunjieFinance
 * author : dluobida
 * class : DbHelperImpl.java
 * package : com.dluobida.junjiefinance.core.db.DbHelperImpl
 * currentModifyTime : 2019-11-27 23:10:09
 * lastModifyTime : 2019-11-27 23:10:09
 * Copyright (c) 2019 dluobida .
 */

package com.dluobida.bluecat.finance.core.db;

import android.database.sqlite.SQLiteDatabase;

import com.dluobida.bluecat.finance.base.application.BaseApplication;
import com.dluobida.bluecat.finance.core.constant.Constants;
import com.dluobida.bluecat.finance.core.db.table.AccountData;
import com.dluobida.bluecat.finance.core.db.table.IncomeData;
import com.dluobida.bluecat.finance.core.greendao.DaoMaster;
import com.dluobida.bluecat.finance.core.greendao.DaoSession;
import com.dluobida.bluecat.finance.core.db.table.ExpandData;
import com.dluobida.bluecat.finance.utils.LogUtils;
import com.dluobida.bluecat.finance.utils.MathMoneyUtils;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

public class DbHelperImpl implements DbHelper{

    private DaoSession daoSession;
    @Inject
    DbHelperImpl(){
        initGreenDao();
    }

    private void initGreenDao(){
        DaoMaster.DevOpenHelper devOpenHelper =
                new DaoMaster.DevOpenHelper(BaseApplication.getContext(), Constants.DB_NAME);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();

    }


    @Override
    public void saveExpandData(ExpandData expandData) {
        daoSession.getExpandDataDao().insert(expandData);
    }

    @Override
    public List<ExpandData> queryAllExpandData() {
        return daoSession.getExpandDataDao().loadAll();
    }

    @Override
    public void saveIncomeData(IncomeData incomeData) {
        daoSession.getIncomeDataDao().insert(incomeData);
    }

    @Override
    public List<IncomeData> queryAllIncomeData() {
        return daoSession.getIncomeDataDao().loadAll();
    }

    @Override
    public void saveAccountData(AccountData accountData) {
        daoSession.getAccountDataDao().insert(accountData);
    }

    @Override
    public List<AccountData> queryAllAccountData() {
        return daoSession.getAccountDataDao().loadAll();
    }

    @Override
    public void updateAccountData(String accountName, String money) {
        List<AccountData> datas = daoSession.getAccountDataDao().queryRaw("where name=?", accountName);
        LogUtils.i("accountData=" + datas.toString());
        AccountData updateAccountData = datas.get(0);
        LogUtils.i("originMoney=" + updateAccountData.getMoney() + " expandMoney=" + money);
        String updateMoney = MathMoneyUtils.sub(updateAccountData.getMoney(),money);
        LogUtils.i("updateMoney=" + updateMoney);
        updateAccountData.setMoney(updateMoney);
        daoSession.getAccountDataDao().update(updateAccountData);

    }

}
