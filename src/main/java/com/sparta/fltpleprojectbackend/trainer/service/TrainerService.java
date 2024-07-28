package com.sparta.fltpleprojectbackend.trainer.service;

import com.sparta.fltpleprojectbackend.enums.ErrorType;
import com.sparta.fltpleprojectbackend.security.UserDetailsImpl;
import com.sparta.fltpleprojectbackend.trainer.dto.ReadTrainerResponse;
import com.sparta.fltpleprojectbackend.trainer.dto.TrainerGetResponse;
import com.sparta.fltpleprojectbackend.trainer.dto.UpdateTrainerPasswordRequest;
import com.sparta.fltpleprojectbackend.trainer.dto.UpdateTrainerProfileRequest;
import com.sparta.fltpleprojectbackend.trainer.entity.Trainer;
import com.sparta.fltpleprojectbackend.trainer.exception.TrainerException;
import com.sparta.fltpleprojectbackend.trainer.repository.TrainerRepository;
import com.sparta.fltpleprojectbackend.user.exception.UserException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {

  private final TrainerRepository trainerRepository;
  private final PasswordEncoder passwordEncoder;

  /**.
   * 트레이너 전체 조회
   *
   * @return 트레이너 리스트
   * @throws TrainerException 트레이너를 찾을 수 없는 경우 발생
   */
  public List<TrainerGetResponse> getAllTrainers() {
    return trainerRepository.findAll().stream()
      .map(TrainerGetResponse::new)
      .toList();
  }

  /**.
   *  매장 트레이너 조회
   *
   * @param storeId 스토어 아이디
   * @return 트레이너 리스트
   * @throws TrainerException 트레이너를 찾을 수 없는 경우 발생
   */
  public List<TrainerGetResponse> getStoreTrainers(Long storeId) {
    return trainerRepository.findByStoreIdAndTrainerStatus(storeId, "ACTIVE").stream()
      .map(TrainerGetResponse::new)
      .toList();
  }

  /**.
   * 트레이너 프로필 조회
   *
   * @param userDetails 트레이너 정보
   */
  public ReadTrainerResponse readTrainerProfile(UserDetailsImpl userDetails) {
    Optional<Trainer> trainerOptional = trainerRepository.findByAccountIdAndTrainerStatus(userDetails.getUsername(), "ACTIVE");
    Trainer trainer = trainerOptional.orElseThrow(() -> new TrainerException(ErrorType.NOT_FOUND_TRAINER));
    return new ReadTrainerResponse(trainer);
  }

  /**.
   * 트레이너 프로필 변경
   *
   * @param userDetails 트레이너 정보
   * @param trainerRequest 새 프로필 정보
   * @throws TrainerException 트레이너를 찾을 수 없는 경우 발생, 비밀번호 확인이 틀린 경우
   */
  @Transactional
  public void updateTrainerProfile(UpdateTrainerProfileRequest trainerRequest, UserDetailsImpl userDetails) {
    Optional<Trainer> trainerOptional = trainerRepository.findByAccountIdAndTrainerStatus(userDetails.getUsername(), "ACTIVE");
    Trainer trainer = trainerOptional.orElseThrow(() -> new TrainerException(ErrorType.NOT_FOUND_TRAINER));

    if (!passwordEncoder.matches(trainerRequest.getPassword(), trainer.getPassword())) {
      throw new TrainerException(ErrorType.INVALID_PASSWORD);
    }

    trainer.updateUserProfile(trainerRequest);
  }

  /**.
   * 트레이너 비밀번호 변경
   *
   * @param userDetails 트레이너 정보
   * @param trainerRequest 구, 새 비밀번호 정보
   * @throws TrainerException 트레이너를 찾을 수 없는 경우 발생, 비밀번호 확인이 틀린 경우
   */
  @Transactional
  public void updateTrainerPassword(UpdateTrainerPasswordRequest trainerRequest, UserDetailsImpl userDetails) {
    Optional<Trainer> trainerOptional = trainerRepository.findByAccountIdAndTrainerStatus(userDetails.getUsername(), "ACTIVE");
    Trainer trainer = trainerOptional.orElseThrow(() -> new TrainerException(ErrorType.NOT_FOUND_TRAINER));

    if (!passwordEncoder.matches(trainerRequest.getOldPassword(), trainer.getPassword())) {
      throw new TrainerException(ErrorType.INVALID_PASSWORD);
    }

    trainer.updatePassword(passwordEncoder.encode(trainerRequest.getNewPassword()));
  }
}