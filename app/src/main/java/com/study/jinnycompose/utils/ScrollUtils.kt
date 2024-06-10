@ExperimentalCoroutinesApi
@Composable
fun LazyListState.getScrollDirection(): ScrollDirection {
    var scrollDirection by remember {
        mutableStateOf(ScrollDirection.None)
    }
    var previousOffset by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(this) {
        snapshotFlow { firstVisibleItemScrollOffset }
            .distinctUntilChanged()
            .flatMapLatest { offset ->
                when {
                    offset > previousOffset -> flowOf(ScrollDirection.Down)
                    offset < previousOffset -> flowOf(ScrollDirection.Up)
                    else -> flowOf(ScrollDirection.None)
                }.also {
                    previousOffset = offset
                }
            }
            .collectLatest { direction ->
                scrollDirection = direction
            }
    }
    return scrollDirection
}
