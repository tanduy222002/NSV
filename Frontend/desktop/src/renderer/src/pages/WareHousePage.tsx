import {
    TableView,
    Pagination,
    SearchBar,
    ConfirmationPopup
} from '@renderer/components';
import { ColumnType } from '@renderer/components/TableView';
import { GoBell } from 'react-icons/go';
import { useAppSelector, usePopup } from '@renderer/hooks';
import { ModalProvider } from '@renderer/components';
import {
    CreateRackingLayoutFormController,
    CreateWarehouseFormController
} from '@renderer/features/wms';

const warehouseTableColumns = [
    { title: 'ID', sortable: true, type: ColumnType.Text },
    { title: 'Tên', sortable: true, type: ColumnType.Text },
    { title: 'Loại kho', sortable: false, type: ColumnType.Text },
    { title: 'Địa điểm', sortable: false, type: ColumnType.Text },
    { title: 'Sức chứa hiện tại', sortable: true, type: ColumnType.Text },
    { title: 'Trạng thái', sortable: false, type: ColumnType.Button },
    { title: 'Thao tác', sortable: true, type: ColumnType.Action }
];

const items = [
    {
        ID: 1,
        name: 'Kho hàng 01',
        type: 'Kho hàng lạnh',
        address: 'Tân thới, Phong điền, Cần Thơ',
        capacity: '5000/20000',
        status: 'Hoạt động'
    },
    {
        ID: 2,
        name: 'Kho hàng 01',
        type: 'Kho hàng lạnh',
        address: 'Tân thới, Phong điền, Cần Thơ',
        capacity: '5000/20000',
        status: 'Hoạt động'
    }
];

const WareHousePage = () => {
    const { showPopup, show, hide } = usePopup();

    const user = useAppSelector((state) => state.auth.value);

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
                <div className="flex items-center gap-2">
                    <GoBell />
                    <div>{user?.username}</div>
                </div>
            </div>
            <div className="flex items-center gap-4">
                <ModalProvider>
                    <CreateWarehouseFormController />
                </ModalProvider>
                <ModalProvider>
                    <CreateRackingLayoutFormController />
                </ModalProvider>
            </div>
            <SearchBar className="mt-6 ml-auto" placeHolder="Tìm kiếm..." />
            <div className="flex flex-col gap-4 mt-6">
                <TableView
                    columns={warehouseTableColumns}
                    items={items}
                    deleteAction={show}
                />
                <Pagination maxPage={5} currentPage={1} />
            </div>
        </div>
    );
};

export default WareHousePage;
