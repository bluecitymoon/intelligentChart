(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonSocialMedia', PersonSocialMedia);

    PersonSocialMedia.$inject = ['$resource'];

    function PersonSocialMedia ($resource) {
        var resourceUrl =  'api/person-social-medias/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: 'api/person-social-medias/person/:id',
                params: { id : '@id'}
            }
        });
    }
})();
