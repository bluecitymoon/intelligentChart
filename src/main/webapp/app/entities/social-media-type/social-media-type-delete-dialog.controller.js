(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('SocialMediaTypeDeleteController',SocialMediaTypeDeleteController);

    SocialMediaTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'SocialMediaType'];

    function SocialMediaTypeDeleteController($uibModalInstance, entity, SocialMediaType) {
        var vm = this;

        vm.socialMediaType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SocialMediaType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
