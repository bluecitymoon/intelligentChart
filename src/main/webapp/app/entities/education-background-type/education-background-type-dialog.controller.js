(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('EducationBackgroundTypeDialogController', EducationBackgroundTypeDialogController);

    EducationBackgroundTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EducationBackgroundType'];

    function EducationBackgroundTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EducationBackgroundType) {
        var vm = this;

        vm.educationBackgroundType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.educationBackgroundType.id !== null) {
                EducationBackgroundType.update(vm.educationBackgroundType, onSaveSuccess, onSaveError);
            } else {
                EducationBackgroundType.save(vm.educationBackgroundType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:educationBackgroundTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
