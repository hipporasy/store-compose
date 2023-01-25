package codes.hipporasy.store.domain.useCase

import codes.hipporasy.store.data.model.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<in Params, ReturnType> where ReturnType : Any {

    protected abstract suspend fun FlowCollector<DataState<ReturnType>>.execute(params: Params)

    suspend operator fun invoke(params: Params) = flow {
        execute(params)
    }.flowOn(Dispatchers.IO)
}
