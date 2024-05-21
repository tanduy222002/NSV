import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { useLocalStorage, usePagination } from '@renderer/hooks';
import { getProductListStatistic } from '@renderer/services/api';
import {
    TableView,
    Button,
    TableSkeleton,
    Pagination
} from '@renderer/components';
import { ColumnType } from '@renderer/components/TableView';
import { ProductLineItem } from '@renderer/types/product';
import { formatNumber } from '@renderer/utils/formatText';

const productTableConfig = [
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
        title: 'Tên sản phẩm',
        sortable: true,
        type: ColumnType.Text
    },
    {
        title: 'Tồn kho',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Số lô chứa',
        sortable: false,
        type: ColumnType.Text
    },
    {
        title: 'Thao tác',
        sortable: false,
        type: ColumnType.Action
    }
];

const ProductList = () => {
    const navigate = useNavigate();

    const goToCreateProductPage = () => navigate('/product/create');
    const goToEditProductPage = () => navigate('/product/edit');
    const goToOverview = (productId: number | string) => {
        navigate(`/product/${productId}/location`);
    };

    const [maxPage, setMaxPage] = useState(1);
    const { currentPage, goNext, goBack, goToPage } = usePagination(maxPage);

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { isFetching, data } = useQuery({
        queryKey: ['products', currentPage],
        queryFn: async () => {
            const response = await getProductListStatistic({
                token: accessToken,
                pageIndex: currentPage,
                pageSize: 5
            });
            setMaxPage(response?.total_page);
            return response;
        }
    });

    const mapProductData = (products: ProductLineItem[]) =>
        products.map((product) => ({
            image: product.image,
            id: product.id,
            name: product.name,
            inventory: `${formatNumber(product.inventory)} kg`,
            containingSlots: product.number_of_containing_slot
        })) ?? [];

    return (
        <div>
            <Button
                text="Thêm sản phẩm"
                className="text-emerald-500 border-emerald-500 hover:bg-emerald-50 mb-5"
                action={goToCreateProductPage}
            />
            <div className="flex flex-col h-full max-h-screen gap-10">
                {isFetching ? (
                    <TableSkeleton />
                ) : (
                    <>
                        <TableView
                            columns={productTableConfig}
                            items={mapProductData(data?.content)}
                            viewAction={goToOverview}
                            editAction={goToEditProductPage}
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

export default ProductList;
