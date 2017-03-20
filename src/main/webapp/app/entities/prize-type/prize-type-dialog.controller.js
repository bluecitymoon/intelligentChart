(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PrizeTypeDialogController', PrizeTypeDialogController);

    PrizeTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PrizeType'];

    function PrizeTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PrizeType) {
        var vm = this;

        vm.prizeType = entity;
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
            if (vm.prizeType.id !== null) {
                PrizeType.update(vm.prizeType, onSaveSuccess, onSaveError);
            } else {
                PrizeType.save(vm.prizeType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:prizeTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
