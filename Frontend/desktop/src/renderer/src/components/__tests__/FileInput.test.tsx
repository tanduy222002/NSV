import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import FileInput from '../FileInput';

describe('FileInput', () => {
    it('renders correctly', () => {
        render(<FileInput onChange={() => {}} />);
        expect(screen.getByText('Thêm ảnh minh họa')).toBeInTheDocument();
    });

    it('triggers onChange event when file is selected', async () => {
        const onChange = jest.fn();
        render(<FileInput onChange={onChange} />);

        const file = new File(['dummy content'], 'test.png', {
            type: 'image/png'
        });
        const input = screen.getByTestId('file-input');
        fireEvent.change(input, { target: { files: [file] } });

        await waitFor(() => {
            expect(onChange).toHaveBeenCalledWith(expect.any(String));
        });
    });

    it('displays default icon when fileSrc is null', () => {
        render(<FileInput onChange={() => {}} />);

        expect(screen.getByTestId('default-icon')).toBeInTheDocument();
    });

    it('displays selected image when fileSrc is provided', () => {
        const fileSrc =
            'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAf0lEQVQ4jWNgGAWjYBSMglEwQA0CgxAwMDI2UcgjIyMCg4GQDgWkIDgPQAKADeZUfwu+2YsAAAAASUVORK5CYII=';
        render(<FileInput fileSrc={fileSrc} onChange={() => {}} />);

        const image = screen.getByAltText('preview-image');
        expect(image).toBeInTheDocument();
        expect(image).toHaveAttribute('src', fileSrc);
    });
});
