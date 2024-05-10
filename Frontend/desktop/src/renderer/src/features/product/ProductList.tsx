import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { useLocalStorage } from '@renderer/hooks';
import { getProductListStatistic } from '@renderer/services/api';
import { TableView, Button, TableSkeleton } from '@renderer/components';
import { ColumnType } from '@renderer/components/TableView';
import { ProductLineItem } from '@renderer/types/product';

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
    const goToOverview = (productId: number) => {
        navigate(`/product/${productId}/location`);
    };

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { isFetching, data } = useQuery({
        queryKey: ['products'],
        queryFn: () =>
            getProductListStatistic({
                token: accessToken,
                pageIndex: 1,
                pageSize: 5
            })
    });

    const mapProductData = (products: ProductLineItem[]) =>
        products.map((product) => ({
            image: product.image,
            id: product.id,
            name: product.name,
            inventory: `${product.inventory} kg`,
            containingSlots: product.number_of_containing_slot
        }));

    return (
        <div>
            <Button
                text="Thêm sản phẩm"
                className="text-[#008767] border-[#008767] bg-[#16C098] mb-6"
                action={goToCreateProductPage}
            />
            <div className="flex h-full max-h-screen gap-10">
                {isFetching ? (
                    <TableSkeleton />
                ) : (
                    <TableView
                        columns={productTableConfig}
                        items={mapProductData(data!.content)}
                        viewAction={goToOverview}
                        editAction={goToEditProductPage}
                    />
                )}
            </div>
        </div>
    );
};

export default ProductList;
