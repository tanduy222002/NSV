import { useNavigate, useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { IoChevronBack } from 'react-icons/io5';
import fruitIconSrc from '../assets/fruit.png';
import { useLocalStorage, usePagination } from '@renderer/hooks';
import { getProductLocation } from '@renderer/services/api';
import {
    UserInfo,
    TableView,
    TableSkeleton,
    Pagination
} from '@renderer/components';
import { ColumnType } from '@renderer/components/TableView';
import { useState } from 'react';

const locationTableConfig = [
    {
        title: '',
        sortable: false,
        type: ColumnType.Image
    },
    {
        title: 'ID',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Loại sản phẩm',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Diện tích',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Khối lượng',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Vị trí',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Thao tác',
        sortable: false,
        type: ColumnType.Action
    }
];

const ProductLocationDetailPage = () => {
    const navigate = useNavigate();
    const goToProductPage = () => navigate('/product');

    const { productId } = useParams();

    const [maxPage, setMaxPage] = useState(1);
    const { currentPage, goNext, goBack, goToPage } = usePagination(maxPage);

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();
    const { isFetching, data } = useQuery({
        queryKey: ['product-location', productId, currentPage],
        queryFn: async () => {
            const response = await getProductLocation({
                token: accessToken,
                productId: productId!,
                pageIndex: 1,
                pageSize: 10
            });
            setMaxPage(response?.total_page);
            return response;
        }
    });

    const mapLocationTable = (productDetails) =>
        productDetails.map((detail) => ({
            image: detail?.image,
            id: detail?.slot_id,
            name: detail?.type_with_quality,
            area: `${detail?.containing_area} m²`,
            weight: `${detail?.weight} kg`,
            location: detail?.location,
            warehouseId: detail?.warehouse_id,
            viewAction: () =>
                navigate(
                    `/warehouse/${detail?.warehouse_id}/slot/${detail?.slot_id}`
                )
        }));

    if (!isFetching) console.log(data);

    return (
        <div className=" w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToProductPage}
                />
                <img src={fruitIconSrc} />
                <h1 className="font-semibold text-xl">Vị trí chứa hàng</h1>
            </div>
            <div className="flex flex-col gap-4 max-w-[700px]">
                {isFetching ? (
                    <TableSkeleton />
                ) : (
                    <>
                        <TableView
                            columns={locationTableConfig}
                            items={mapLocationTable(data)}
                        />
                        <Pagination
                            currentPage={currentPage}
                            maxPage={maxPage}
                            goBack={goBack}
                            goNext={goNext}
                            goToPage={goToPage}
                        />
                    </>
                )}
            </div>
        </div>
    );
};

export default ProductLocationDetailPage;
