/*
 * MROZA - supporting system of behavioral therapy of people with autism
 *     Copyright (C) 2015-2016 autyzm-pg
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package syncmodels;

import syncmodels.TransferChildTable;
import syncmodels.TransferTableFieldFilling;

import java.util.ArrayList;
import java.util.List;


public class SendSyncModel {

    private List<TransferTableFieldFilling> transferTableFieldFillingList;
    private List<TransferChildTable> transferChildTableList;

    public SendSyncModel() {
        this.transferChildTableList = new ArrayList<>();
        this.transferTableFieldFillingList = new ArrayList<>();
    }

    public void setTransferTableFieldFillingList(List<TransferTableFieldFilling> transferTableFieldFillingList) {
        this.transferTableFieldFillingList = transferTableFieldFillingList;
    }

    public void setTransferChildTableList(List<TransferChildTable> transferChildTableList) {
        this.transferChildTableList = transferChildTableList;
    }

    public List<TransferTableFieldFilling> getTransferTableFieldFillingList() {
        return transferTableFieldFillingList;
    }

    public List<TransferChildTable> getTransferChildTableList() {
        return transferChildTableList;
    }
}

