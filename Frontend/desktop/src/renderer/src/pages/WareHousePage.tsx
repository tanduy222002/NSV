import { useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import {
    TableView,
    TableSkeleton,
    Pagination,
    SearchBar,
    ConfirmationPopup,
    UserInfo
} from '@renderer/components';
import { ColumnType } from '@renderer/components/TableView';
import { useDeferredState, usePagination, usePopup } from '@renderer/hooks';
import { Button } from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import { searchWarehouse } from '@renderer/services/api';
import { Warehouse } from '@renderer/types/warehouse';
import { useState } from 'react';

const warehouseTableColumns = [
    { title: 'ID', sortable: true, type: ColumnType.Text },
    { title: 'Tên', sortable: true, type: ColumnType.Text },
    { title: 'Loại kho', sortable: false, type: ColumnType.Text },
    { title: 'Địa điểm', sortable: false, type: ColumnType.Text },
    { title: 'Sức chứa hiện tại', sortable: false, type: ColumnType.Text },
    { title: 'Trạng thái', sortable: false, type: ColumnType.Text },
    { title: 'Thao tác', sortable: false, type: ColumnType.Action }
];

const WareHousePage = () => {
    const [maxPage, setMaxPage] = useState(1);
    const { currentPage, goBack, goNext, goToPage } = usePagination(
        maxPage ?? 1
    );

    const navigate = useNavigate();
    const goToCreateMapPage = () => navigate('/warehouse/map/create');
    const goToCreateWarehousePage = () => navigate('/warehouse/create');
    const goToWarehouseDetailPage = (id: number | string) =>
        navigate(`/warehouse/${id}`);
    const { showPopup, show, hide } = usePopup();

    const [searchValue, setSearchValue] = useDeferredState('');

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data, isFetching } = useQuery({
        queryKey: ['warehouse', currentPage, searchValue],
        queryFn: async () => {
            const response = await searchWarehouse({
                token: accessToken,
                pageIndex: currentPage,
                name: searchValue
            });
            setMaxPage(response?.total_page);
            return response;
        }
    });

    const mapWarehouseTable = (warehouses: Warehouse[]) =>
        warehouses?.map((warehouse) => ({
            id: warehouse?.id,
            name: warehouse?.name,
            type: warehouse?.type,
            address: warehouse?.address_string,
            capacity: warehouse?.current_capacity,
            status: warehouse?.status
        })) ?? [];

    if (!isFetching) console.log('data: ', data);

    return (
        <div className="w-full p-10 relative">
            {showPopup && (
                <ConfirmationPopup
                    title="Xóa sản phẩm"
                    body="Xóa sản phẩm sẽ xóa tất cả thông tin liên quan"
                    cancelAction={hide}
                    confirmAction={hide}
                />
            )}
            <UserInfo />
            <div className="flex justify-between items-center w-full mb-6">
                <h1 className="text-2xl font-semibold">Quản lý kho hàng</h1>
            </div>
            <div className="flex items-center gap-4 mb-5">
                <Button
                    text="Tạo sơ đồ kho"
                    className="text-emerald-500 border-emerald-500 hover:bg-emerald-50"
                    action={goToCreateMapPage}
                />
                <Button
                    text="Tạo kho mới"
                    className="text-emerald-500 border-emerald-500 hover:bg-emerald-50"
                    action={goToCreateWarehousePage}
                />
            </div>
            <div className="flex flex-col gap-4 mt-6 w-[1200px]">
                <SearchBar
                    className="ml-auto"
                    placeHolder="Tìm kho..."
                    updateSearchValue={setSearchValue}
                />
                {isFetching ? (
                    <TableSkeleton />
                ) : (
                    <>
                        <TableView
                            columns={warehouseTableColumns}
                            items={mapWarehouseTable(data?.content)}
                            deleteAction={show}
                            viewAction={goToWarehouseDetailPage}
                        />
                        <Pagination
                            maxPage={maxPage}
                            currentPage={currentPage}
                            goNext={goNext}
                            goBack={goBack}
                            goToPage={goToPage}
                        />
                    </>
                )}
            </div>
        </div>
    );
};

export default WareHousePage;
