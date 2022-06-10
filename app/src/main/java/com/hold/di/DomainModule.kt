package com.hold.di

import com.hold.domain.usecase.user.GetUserResultsUseCase
import com.hold.domain.usecase.user.SetNewResultUseCase
import org.koin.dsl.module

val useCaseModule = module {

    //Auth
//    factory { CheckEmailExistsUseCase(get()) }
//
//    factory { SignUpUseCase(get(), get()) }
//
//    factory { VerifyOtpCodeEmailUseCase(get()) }
//
//    factory { ResendOtpCodeUseCase(get()) }
//
//    factory { LoginUseCase(get(), get()) }
//
//    factory { ResetPasswordUseCase(get()) }
//
//    factory { RestorePasswordUseCase(get()) }
//
//    //Profile
//    factory { UpdateProfileUseCase(get(), get()) }
//
//    factory { GetUserInterestsUseCase(get()) }
//
//    factory { PostUserInterestsUseCase(get()) }

    //User

    factory { GetUserResultsUseCase(get()) }

    factory { SetNewResultUseCase(get()) }


}



