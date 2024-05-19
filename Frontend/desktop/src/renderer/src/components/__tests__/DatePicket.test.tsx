import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import DatePicker from '../DatePicker';

describe('DatePicker component', () => {
    const setup = (props = {}) => {
        const initialProps = {
            name: 'Test Date Picker',
            placeHolder: 'Select date',
            value: null,
            onChange: jest.fn(),
            ...props
        };
        return render(<DatePicker {...initialProps} />);
    };

    test('should render without crashing', () => {
        setup();
        expect(screen.getByText('Test Date Picker')).toBeInTheDocument();
    });

    test('should display the name', () => {
        setup();
        const name = screen.getByText('Test Date Picker');

        expect(name).toBeInTheDocument();
    });
});
