(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('LawBusinessTypeDialogController', LawBusinessTypeDialogController);

    LawBusinessTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LawBusinessType'];

    function LawBusinessTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LawBusinessType) {
        var vm = this;

        vm.lawBusinessType = entity;
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
            if (vm.lawBusinessType.id !== null) {
                LawBusinessType.update(vm.lawBusinessType, onSaveSuccess, onSaveError);
            } else {
                LawBusinessType.save(vm.lawBusinessType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:lawBusinessTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
