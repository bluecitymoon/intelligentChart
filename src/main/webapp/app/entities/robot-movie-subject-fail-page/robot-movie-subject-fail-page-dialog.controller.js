(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('RobotMovieSubjectFailPageDialogController', RobotMovieSubjectFailPageDialogController);

    RobotMovieSubjectFailPageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RobotMovieSubjectFailPage'];

    function RobotMovieSubjectFailPageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RobotMovieSubjectFailPage) {
        var vm = this;

        vm.robotMovieSubjectFailPage = entity;
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
            if (vm.robotMovieSubjectFailPage.id !== null) {
                RobotMovieSubjectFailPage.update(vm.robotMovieSubjectFailPage, onSaveSuccess, onSaveError);
            } else {
                RobotMovieSubjectFailPage.save(vm.robotMovieSubjectFailPage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:robotMovieSubjectFailPageUpdate', result);
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
