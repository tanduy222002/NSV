import { useDispatch } from 'react-redux';
import type { AppDispatch } from '@renderer/store/store';

const useAppDispatch: () => AppDispatch = useDispatch;

export default useAppDispatch;
