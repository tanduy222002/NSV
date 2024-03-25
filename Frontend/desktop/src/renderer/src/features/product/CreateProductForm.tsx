import { useState } from 'react';
import { Formik } from 'formik';
import { FormInput, Button } from '@renderer/components';
import FileUploadInput from './FileUploadInput';
import FormValuesPreview from './FormValuesPreview';

type CreateProductFormValues = {
    name: string;
    imageUrl: string;
    categories: string[];
    qualities: string[];
};

const createProductFormInitValues: CreateProductFormValues = {
    name: '',
    imageUrl: '',
    categories: [],
    qualities: []
};

const CreateProductForm = () => {
    const [fileSrc, setFileSrc] = useState<File | undefined>();

    const handleFileChange = (fileSrc: File) => {
        setFileSrc(fileSrc);
    };

    return (
        <Formik initialValues={createProductFormInitValues} onSubmit={() => {}}>
            {({ values, handleChange }) => (
                <form className="flex flex-col items-center">
                    <h1 className="text-xl font-semibold mb-5">
                        Thêm sản phẩm mới
                    </h1>
                    <div className="flex flex-col gap-5">
                        <FormInput
                            name="name"
                            label="Tên sản phẩm"
                            value={values.name}
                            onChange={handleChange}
                        />

                        <FileUploadInput
                            fileSrc={fileSrc}
                            setFileSrc={handleFileChange}
                        />

                        <FormValuesPreview
                            name="categories"
                            label="Chủng loại"
                            values={values.categories}
                        />
                        <FormValuesPreview
                            name="qualities"
                            label="Chất lượng"
                            values={values.qualities}
                        />

                        <Button
                            text="Lưu sản phẩm"
                            type="submit"
                            className="text-[#008767] border-[#008767] mx-auto"
                        />
                    </div>
                </form>
            )}
        </Formik>
    );
};

export default CreateProductForm;
