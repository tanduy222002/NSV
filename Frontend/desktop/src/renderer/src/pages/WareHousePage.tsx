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
import { usePopup } from '@renderer/hooks';
import { Button } from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import { searchWarehouse } from '@renderer/services/api';
import { Warehouse } from '@renderer/types/warehouse';

const warehouseTableColumns = [
    { title: 'ID', sortable: true, type: ColumnType.Text },
    { title: 'Tên', sortable: true, type: ColumnType.Text },
    { title: 'Loại kho', sortable: false, type: ColumnType.Text },
    { title: 'Địa điểm', sortable: false, type: ColumnType.Text },
    { title: 'Sức chứa hiện tại', sortable: true, type: ColumnType.Text },
    { title: 'Trạng thái', sortable: false, type: ColumnType.Text },
    { title: 'Thao tác', sortable: false, type: ColumnType.Action }
];

const WareHousePage = () => {
    const navigate = useNavigate();
    const goToCreateMapPage = () => navigate('/warehouse/map/create');
    const goToCreateWarehousePage = () => navigate('/warehouse/create');
    const goToWarehouseDetailPage = (id: number) =>
        navigate(`/warehouse/${id}`);
    const { showPopup, show, hide } = usePopup();

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data, isFetching } = useQuery({
        queryKey: ['warehouse'],
        queryFn: () =>
            searchWarehouse({
                token: accessToken,
                pageIndex: 1
            })
    });

    const mapWarehouseTable = (warehouses: Warehouse[]) =>
        warehouses?.map((warehouse) => ({
            id: warehouse?.id,
            name: warehouse?.name,
            type: warehouse?.type,
            address: warehouse?.address_string,
            capacity: warehouse?.current_capacity,
            status: warehouse?.status
        }));

    if (!isFetching) console.log('data: ', data);

    return (
        <div className="flex-1 h-screen px-8 py-6 relative">
            {showPopup && (
                <ConfirmationPopup
                    title="Xóa sản phẩm"
                    body="Xóa sản phẩm sẽ xóa tất cả thông tin liên quan"
                    cancelAction={hide}
                    confirmAction={hide}
                />
            )}
            <div className="flex justify-between items-center w-full mb-6">
                <h1 className="text-2xl font-semibold">Quản lý kho hàng</h1>
                <UserInfo />
            </div>
            <div className="flex items-center gap-4">
                <Button
                    text="Tạo sơ đồ kho"
                    className="text-[#008767] border-[#008767] bg-[#16C098]"
                    action={goToCreateMapPage}
                />
                <Button
                    text="Tạo kho mới"
                    className="text-[#008767] border-[#008767] bg-[#16C098]"
                    action={goToCreateWarehousePage}
                />
            </div>
            <SearchBar className="mt-6 ml-auto" placeHolder="Tìm kiếm..." />
            <div className="flex flex-col gap-4 mt-6">
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
                        <Pagination maxPage={5} currentPage={1} />
                    </>
                )}
            </div>
        </div>
    );
};

export default WareHousePage;
