import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { useLocalStorage } from '@renderer/hooks';
import { makeGetProductListRequest } from '@renderer/services/api';
import { TableView, Button, TableSkeleton } from '@renderer/components';
import { ColumnType } from '@renderer/components/TableView';

const productTableConfig = [
    {
        title: 'ID',
        sortable: false,
        type: ColumnType.Text
    },
    // { title: '', sortable: false, type: ColumnType.Image },
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
        title: 'Thao tác',
        sortable: false,
        type: ColumnType.Action
    }
];

const ProductList = () => {
    const navigate = useNavigate();

    const goToCreateProductPage = () => navigate('/product/create');
    const goToEditProductPage = () => navigate('/product/edit');

    const { getItem } = useLocalStorage('access-token');
    const jwtToken = getItem();

    const { isLoading, data } = useQuery({
        queryKey: ['products'],
        queryFn: () => makeGetProductListRequest(jwtToken, 1, 5)
    });

    const goToOverview = (productId: number) => {
        navigate(`/product/${productId}`);
    };

    return (
        <div>
            <div>
                <Button
                    text="Thêm sản phẩm"
                    className="text-[#008767] border-[#008767] bg-[#16C098] mb-6"
                    action={goToCreateProductPage}
                />
                <div className="flex h-full max-h-screen gap-10">
                    {isLoading ? (
                        <TableSkeleton />
                    ) : data?.length === 0 ? (
                        <p>Hiện chưa có sản phẩm nào</p>
                    ) : (
                        <TableView
                            columns={productTableConfig}
                            items={data!}
                            viewAction={goToOverview}
                            editAction={goToEditProductPage}
                        />
                    )}
                </div>
            </div>
        </div>
    );
};

export default ProductList;
