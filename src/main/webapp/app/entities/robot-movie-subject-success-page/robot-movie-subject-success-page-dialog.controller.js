(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotMovieSubjectSuccessPageDialogController', RobotMovieSubjectSuccessPageDialogController);

    RobotMovieSubjectSuccessPageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RobotMovieSubjectSuccessPage'];

    function RobotMovieSubjectSuccessPageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RobotMovieSubjectSuccessPage) {
        var vm = this;

        vm.robotMovieSubjectSuccessPage = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.robotMovieSubjectSuccessPage.id !== null) {
                RobotMovieSubjectSuccessPage.update(vm.robotMovieSubjectSuccessPage, onSaveSuccess, onSaveError);
            } else {
                RobotMovieSubjectSuccessPage.save(vm.robotMovieSubjectSuccessPage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:robotMovieSubjectSuccessPageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
