import { TableView } from '@renderer/components';
import { ColumnType } from '@renderer/components/TableView';
import { ProductOverview } from '@renderer/features/product';
import fruitSrc from '../assets/fruit.png';

const ProductPage = () => {
    const productTableConfig = [
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
            title: 'Thao tác',
            sortable: false,
            type: ColumnType.Action
        }
    ];
    const products = [
        {
            id: 1,
            name: 'Sầu riêng',
            quantity: 15000
        },
        {
            id: 2,
            name: 'Táo',
            quantity: 15000
        },
        {
            id: 3,
            name: 'Dâu tây',
            quantity: 15000
        },
        {
            id: 4,
            name: 'Chuối',
            quantity: 15000
        }
    ];

    return (
        <div className="w-full h-screen px-10 py-10">
            <h1 className="flex items-center gap-2 font-semibold text-xl ">
                <img src={fruitSrc} /> <p>Sản phẩm</p>
            </h1>
            <div className="flex h-full items-center gap-10">
                <TableView columns={productTableConfig} items={products} />
                <div className="w-[2px] h-9/10 bg-[#EEEEEE] mx-4" />
                <ProductOverview />
            </div>
        </div>
    );
};

export default ProductPage;
